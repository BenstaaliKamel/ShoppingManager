import message from "./AxiosCreate"
/**
* Adds a payment to Axios. This is a way to send money to an account without having to wait for the payment to arrive
* 
* @param number - The number of the payment
* @param name - The name of the payment ( can be used in future calls )
* @param expiration - The expiration date of the payment ( must be in YYYY - MM - DD format )
* @param securityNumber - The security number of the payment ( must be in YYYY - MM - DD
*/
async function addPayment(number,name,expiration,securityNumber){
    const response =
    await message.Axios.post("/api/payment",
        {
            "number":number,
            "name":name,
            "expiration":expiration,
            "securityNumber":securityNumber
            
        }
        ,
        {
            headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
            token: localStorage.getItem("token")
            }
        }
        )
      console.log(response)
}
/**
* Fetches cards from Axios and stores them in local storage. This is a blocking call so you don't have to wait for it to complete
* 
* 
* @return { Promise } A promise that resolves with an array of
*/
async function fetchCards(){
    return await message.Axios.get("/api/payment"
        ,
        {
            headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
            token: localStorage.getItem("token")
            }
        }
        )
}
/**
* Delete cards from Axios by id. This is a blocking call so you don't have to wait for the response
* 
* @param id - The id of the
*/
async function deleteCards(id){
    const response =
    await message.Axios.delete("/api/payment/"+id
        ,
        {
            headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
            token: localStorage.getItem("token")
            }
        }
        )
      console.log(response)
}
/**
* Removes a card from the user's hand. This is a low - level function used by cards.
* 
* @param id - The ID of the card to remove
*/
async function removeCard(id){
    message.fetchCsrf().then(()=>{deleteCards(id)})
}
/**
* Fetches a list of registered cards. This is used to determine if we have a card or not and if it's an auth token.
* 
* 
* @return { Promise } A promise that resolves with an array of card objects that were registered in Snapchat
*/
async function getRegisteredCards(){
    return message.fetchCsrf().then(() => {return fetchCards()})
}
/**
* Creates a payment and adds it to the payment history. This is a wrapper around addPayment that fetches the CSRF token from the server.
* 
* @param number - The number of the payment you wish to create.
* @param name - The name of the payment. Should be unique within the bank.
* @param expiration - The expiration date of the payment. If it's 0 the payment will be valid until the end of the account.
* @param securityNumber - The security number of the payment. If it's 0 the payment will be valid until the end of the account
*/
async function paymentCreation(number,name,expiration,securityNumber){
    message.fetchCsrf().then(() => { addPayment(number,name,expiration,securityNumber)});
}
export const payment = {paymentCreation,getRegisteredCards,removeCard,message}