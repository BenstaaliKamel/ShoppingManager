import message from "./AxiosCreate"
/**
* Fetches transaction by id. This is used to verify that user is authorized to access transaction. The token is stored in local storage for future use
* 
* @param id - Id of transaction to
*/
async function fetchTransactionById(id){
    const response =
    await message.Axios.get("/api/transaction/"+id,{
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          token: localStorage.getItem("token")
        }
      })
      console.log(response)
}
/**
* Fetches the transaction from Axios and stores it in local storage. This is used to prevent accidental access to the Transaction API
* 
* 
* @return { Promise } The promise
*/
async function fetchTransaction(){
    
    return await message.Axios.get("/api/transaction/",{
        headers: {
          Authorization: `Bearer ${localStorage.getItem("token")}`,
          token: localStorage.getItem("token")
        }
      })
}
async function creerTransaction(payment_id){
    return await message.Axios.post("/api/transaction/"+payment_id,{},{
        headers: {
            Authorization: `Bearer ${localStorage.getItem("token")}`,
            token: localStorage.getItem("token")
            }
      })
}
/**
* Cheque a transaction. This is a cheque transaction that you don't have to worry about if you are chequeing or not
* 
* @param id - The id of the transaction
* @param number - The number of the account to cheque.
* 
* @return { Promise } Resolves to the response from Axios or rejects with an error message if there is a problem
*/
async function transactionCheque(id,number){
    return await message.Axios.post("/api/transaction/cheque",
        {
            "id":id,
            "account":{
                "number":number
            }
        }
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
* Check if transaction is active by issuing a post request to the api. This is used to prevent accidental access to the api
* 
* @param id - ID of the transaction to check
* 
* @return { Promise } The response from Axios or reject if there is an error. In case of success the response will contain the
*/
async function checkTransactionState(id){
    return await message.Axios.post("/api/transaction/procedure/"+id,{}
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
* Deletes a transaction from Axios. This is a soft delete so you need to pass the transaction ID to this function
* 
* @param id - The ID of the
*/
async function deleteTransaction(id){
    const response =
    await message.Axios.delete("/api/transaction/"+id
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
* Fetches a transaction by id. This is a wrapper around fetchTransactionById that handles CSRF. The user must have permission to view the transaction before calling this function
* 
* @param id - The id of the transaction to
*/
async function getOneTransaction(id){
    message.fetchCsrf().then(()=>{fetchTransactionById(id)})
}
/**
* Fetches all transactions from the server. This is a wrapper around fetchTransaction that does not require a CSRF token.
* 
* 
* @return { Promise } Resolves with an array of { Transaction } objects or rejects with an error message
*/
async function getAllTransaction(){
    return await message.fetchCsrf().then(()=>{return fetchTransaction()})
}
/**
* Pay with a card. This is a wrapper around creerTransaction that checks for CSRF before paying
* 
* @param payment_id - The ID of the card to pay
* 
* @return { Promise } A promise that fulfills when
*/
async function payWithCard(payment_id){
    return await message.fetchCsrf().then(() => {return creerTransaction(payment_id)})
}
/**
* Pay with Cheque. This is a low - level function that calls transactionCheque to do the heavy lifting.
* 
* @param id - The id of the transaction we're paying with.
* @param number - The number of the transaction we're paying with.
* 
* @return { Promise } Resolves with the result of the transactionCheque call or rejects with an error
*/
async function payWithCheque(id,number){
    return await message.fetchCsrf().then(() => {return transactionCheque(id,number)});
}
/**
* Checks the state of a payment. This is a wrapper around checkTransactionState that fetches the CSRF token from the server before performing the check.
* 
* @param id - The id of the payment to check. This should be the same as the transaction id
* 
* @return { Promise } Resolves with the result of the
*/
async function checkPayment(id){
    return await message.fetchCsrf().then(()=>{return checkTransactionState(id)})
}
/**
* Removes a transaction from history. This is used when we want to cancel a transaction that is in progress.
* 
* @param id - The id of the transaction to remove from history
*/
async function removeTransactionFromHistory(id){
    message.fetchCsrf().then(()=>{deleteTransaction(id)})
}

export const transaction = {payWithCard,payWithCheque,getAllTransaction,getOneTransaction,checkPayment,removeTransactionFromHistory,message}