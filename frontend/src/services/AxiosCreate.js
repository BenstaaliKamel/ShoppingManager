import axios from "axios";
const Axios = axios.create({
    baseURL: "http://34.125.87.139:4000",
    withCredentials: true,
    
})

/**
* Fetch CSRF token from api and return response if success return response else return error message to console TODO : move to
*/
async function fetchCsrf(){
    // try{
    //     const response = await Axios.get('/api/csrf-token');
        
    //     return response

    // }catch(e){
    //     console.log(e.response.status)
        
    // }
    
    
}
/**
* Handles errors that occur when the connection is lost. This is a callback from the connection manager to allow us to determine if we should log in or not
* 
* @param err - The error that occurred.
* @param router - The router that was used to make the request.
* 
* @return { Promise } Resolves to the next router in the chain or rejects with an error message if there was
*/
async function handleUnconnected(err,router){
    // If the response is 403 then the router is pushed to the router.
    if(err !=null && err.response != null && err.response.status === 403){
        router.push({name:"Authentification"})
        return;
    }
    console.error("An error occurred:", err);

}

export default {Axios,fetchCsrf,handleUnconnected};