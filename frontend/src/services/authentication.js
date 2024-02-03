import message from "./AxiosCreate"

/**
* Authenticates with Axios and stores the token in local storage. This is a wrapper around the api / signin call to ensure we don't accidentally get a token for each request
* 
* @param email - The email to use for authentication
* @param password - The password to use for authentication. This should be clear text
* 
* @return { boolean } True if the authentication was successful false otherwise. In this case the user will be redirected
*/
async function authenhticate(email,password){
    try{
        const response = 
        await message.Axios.post(`/api/authentication/signin`
        ,
            {
                "email":email,
                "password":password
            },
                );
        localStorage.setItem('token', response.data.value1);
        localStorage.setItem('name',response.data.value0.nom)
        localStorage.setItem('email',response.data.value0.email)
        localStorage.setItem('role',response.data.value0.admin)
        console.log(response.data.value0)
        return response.status;
    }catch(e){
        console.log(e)
    }
            
}
/**
* Creates a new account and stores it in local storage. This is a wrapper around Axios. post ()
* 
* @param nom - The Nom name of the account
* @param email - The email address of the account
* @param password - The password of the account ( unencrypted )
* 
* @return { string } Status of the request. True if success otherwise false. Error object if an error occurred
*/
async function createAccount(nom,email,password){
    try{
        const response = 
        await message.Axios.post(`/api/authentication/signup`
        ,
            {
                "name":nom,
                "email":email,
                "password":password
            }
                );
        localStorage.setItem('token', response.data.value1);
        localStorage.setItem('name',response.data.value0.nom)
        localStorage.setItem('email',response.data.value0.email)
        localStorage.setItem('role',response.data.value0.admin)
        return response.status;
    }catch(e){
        return e.response.status;
    }
}

/**
* Get list of admin users and their access / decline / deactivation status from Axios. com
*/
async function getItems(){
    const response =
    await message.Axios.get('/api/v1/test/admins',{
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          token: localStorage.getItem("token")
        }
      })
      console.log(response)
}
/**
* Logs out of Axios and removes the token from localStorage to prevent CSRF attacks. This is a non - destructive
*/
async function logoutRequest(){
    const response =
    await message.Axios.post('/api/authentication/logout',{
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          token: localStorage.getItem("token")
        }
      })
      localStorage.setItem('token', null);
      console.log(response);
}

/**
* Get auth token from Axios and store it in local storage. This is a blocking call so you don't have to wait for it to finish before calling this function.
* 
* 
* @return { Promise } A promise that resolves with the auth token or rejects with an error message if there is
*/
async function testToken(){
    let t= await message.Axios.get('/api/authentication/',{
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          token: localStorage.getItem("token")
        }
      })
      console.log(t)
      return t;
}
/**
* Sign in to the API. This is a low - level function that does not return a promise.
* 
* @param email - The email to use for authentication. This should be a string in the format ` user@domain. tld `.
* @param password - The password to use for authentication. This should be a string in the format ` user@domain. tld `.
* 
* @return { Promise } Resolves with the signed in user's data or rejects with an error message
*/
async function signin(email,password){
    return message.fetchCsrf().then(() =>authenhticate(email,password));
}
/**
* Fetches and stores the CSRF token for the current user. This is used to check if the user is logged in
*/
async function getter(){
    message.fetchCsrf().then(() =>getItems())
}
/**
* Logs out the user from the server and refreshes the CSRF token. This is a no - op if there is no session
*/
async function logout(){
    message.fetchCsrf().then(() =>logoutRequest())
}
/**
* Creates a new account for the nom. This is a convenience function that calls createAccount with the nom email and password.
* 
* @param nom - The Nom that will be used to signup
* @param email - The email of the user
* @param password - The password of the user ( unencrypted )
* 
* @return { Promise } Resolves with the newly created account or rejects with an error if there was a
*/
async function signup(nom,email,password){
    return message.fetchCsrf().then(() =>createAccount(nom,email,password))
}
async function testAuthentication(){
    return await message.fetchCsrf().then(()=>testToken())
}
/**
* Tests connectivity to the API and if it fails logs the error to the console. This is a workaround for the issue where we don't have a token in localStorage
* 
* @param router - The router to push
*/
async function testConnectivity(router){
    // Check if the token is valid and if it s not expired then redirect to the browser.
    if(localStorage.getItem("token")!=null){
        try{
          let response = await testAuthentication();
          // If the response is 403 then the router will be pushed to the router.
          if(response.status == 403){
            console.log(response)
            router.push({name: "Authentification"});
          }
          
        }catch(e){
          localStorage.setItem("token",null)
          router.push({name: "Authentification"});
          console.error(e)
        }
      }else{
        router.push({name: "Authentification"});
      }
}
export const authentication = {signin,getter,logout,signup,testConnectivity,testAuthentication,message}