import message from "./AxiosCreate"
/**
* Get products by id. This is a wrapper around message. Axios. get so we don't have to worry about token
* 
* @param id - The id of the product to retrieve
* 
* @return { Promise } Resolves with the products that match the id or rejects with an error if there is a
*/
async function getProductsById(id){
    return await message.Axios.get("/api/products/"+id
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
* Get all products from Axios. com and store in local storage. This is used to check if user has access to the API
* 
* 
* @return { Promise } Resolves with response
*/
async function getProducts(){
    return await message.Axios.get("/api/products/"
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
* Creates a new product on Axios. com and returns the product ID. This is a shortcut for sending a POST request to the API with the token that you created in the browser
* 
* @param name - The name of the product
* @param price - The price of the product in cents.
* @param quantity - The quantity of products to create. Must be greater than 0.
* @param image - The image to use as the product's image.
* 
* @return { Promise } Resolves with the product ID or rejects with an error message if something goes wrong
*/
async function createProducts(name,price,quantity,image){
    return await message.Axios.post("/api/products"
        ,
        {
            "name":name,
            "price":price,
            "quantity":quantity,
            "image":image
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
* Update Products in Axios by Product ID ( id ) and name ( name ). This is a convenience method to make it easier to use it in conjunction with other methods that require an API call.
* 
* @param id - The product ID ( product_id ) of the Product you wish to update.
* @param name - The name of the Product you wish to update.
* @param price - The price of the Product you wish to update.
* @param quantity - The quantity of the Product you wish to update.
* @param image - The image that will be used to create the Product
*/
async function updateProducts(id,name,price,quantity,image){
    const response =
    await message.Axios.put("/api/products/"+id
        ,
        {
            "name":name,
            "price":price,
            "quantity":quantity,
            "image":image
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
* Delete products by id. This is a blocking call and will return a promise that resolves when the request completes.
* 
* @param id - The id of the product to delete. This can be found in the response.
* 
* @return { Promise } Resolves with the response from Axios or rejects with an error message if there is an error
*/
async function deleteProducts(id){
    return await message.Axios.delete("/api/products/"+id
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
* Fetches and returns list of products. This is a promise that resolves after the CSRF token has been validated and is used to verify the user's access to the API.
* 
* 
* @return { Promise } Promise that resolves after the CSRF token has been validated and is used to verify the user's access to the API
*/
async function getItems(){
    return await message.fetchCsrf().then(()=>{return getProducts()})
}
/**
* Get items by id. This is a low - level function that calls ` getProductsById ` and returns a promise
* 
* @param id - The id of the item to retrieve
* 
* @return { Promise } Resolves with an array of items matching the id or rejects with an error if there is
*/
async function getItemsById(id){
    return await message.fetchCsrf().then(()=>{return getProductsById(id)})
}
/**
* Adds products to the shopping cart. This is a wrapper around createProducts that fetches the CSRF token and passes the request to the server.
* 
* @param name - The name of the product to add. Must be unique within the shopping cart.
* @param price - The price of the product to add. Must be greater than 0.
* @param quantity - The number of products to add. Must be positive.
* @param image - The image to use as the basis for the product.
* 
* @return { Promise } Resolves with the product
*/
async function addProducts(name,price,quantity,image){
    return await message.fetchCsrf().then(()=>{return createProducts(name,price,quantity,image)})
}
/**
* Changes the products of a product group. This is a wrapper around updateProducts that fetches the CSRF token and sends the request to the server
* 
* @param id - The ID of the group
* @param name - The name of the product group ( ex : Shipping America Lossless )
* @param price - The price of the product group ( ex : 1. 00
* @param quantity - The quantity of products to change
* @param image - The image of the product group ( ex : Bob
*/
async function changeProducts(id,name,price,quantity,image){
    message.fetchCsrf().then(()=>{updateProducts(id,name,price,quantity,image)})
}
/**
* Remove products from a shopping cart. This is a wrapper around deleteProducts that fetches the CSRF token from the server before sending the request
* 
* @param id - The id of the shopping cart to remove products from.
* 
* @return { Promise } Resolves with the result of the request. Rejects if there is an error or the request is invalid
*/
async function removeProducts(id){
    return message.fetchCsrf().then(()=>{return deleteProducts(id)})
}
export const items = {getItems,getItemsById,addProducts,changeProducts,removeProducts,message}