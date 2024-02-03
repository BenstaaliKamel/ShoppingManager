import message from "./AxiosCreate"

/**
* Create panier. This is a shortcut for sending a request to the API. It takes care of saving the Bearer token for use in subsequent requests
* 
* @param product_id - The id of the product to create the panier for
* @param quantity - The number of products to create the panier with
* 
* @return { Promise } The promise resolves with the panier's id or null if there was an error
*/
async function createPanier(product_id,quantity){
    try{
        return await message.Axios.post("/api/panier",
            {
                "quantity":quantity,
                "products":{
                    "id":product_id
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

    }catch(error){
        console.log(error);
        return null;
    }
}
async function getPanier(){
    return await message.Axios.get("/api/panier"
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
* Deletes panier from Axios by id. This is a soft delete so you need to make sure you don't accidentally delete a panier that is in use
* 
* @param id - The id of the
*/
async function deletePanier(id){
    const response =
    await message.Axios.delete("/api/panier/"+id
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
* Deletes Panier from the database. This is a no - op if there is no Panier with the given id
* 
* @param id - The id of the
*/
async function deleteProduct(id){
    message.fetchCsrf().then(()=>{deletePanier(id)})
}
async function getAllPanier(){
    return await message.fetchCsrf().then(()=>{return getPanier()})
}
/**
* Adds a product to Panier. This is a shortcut for creating a new item and adding it to the Panier.
* 
* @param product_id - The ID of the product to add.
* @param quantity - The quantity of the product to add. Must be greater than zero.
* 
* @return { Promise } Resolves with the newly created item or rejects with an error if there was a problem
*/
async function addItemToPanier(product_id,quantity){
    return message.fetchCsrf().then(()=>{return createPanier(product_id,quantity)})
}
export const panier = {addItemToPanier,getAllPanier,deleteProduct,message}
