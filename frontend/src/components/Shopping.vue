<template>
  <div >
    <div v-if="display" style="position: absolute;top:0;left:0;padding-top:20vh;width: 100vw; height: 100vh;background-color: rgba(0, 0, 0, 0.922);z-index: 500;margin:0;" display="{{ disp }}">
        <div class="max-w-sm mx-auto mt-21 border rounded p-10">
          <div class="mb-5">
            <label for="name" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your product's name</label>
            <input  id="name" v-model="name" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="Black top" required>
          </div>
          <div class="mb-5">
            <label for="price" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Yourproduct's price</label>
            <input  id="price" v-model="price" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="500" required>
          </div>
          <div class="mb-5">
            <label for="quantity" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your quantity</label>
            <input  id="quantity" v-model="quantity" class="bg-gray-50 border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5 dark:bg-gray-700 dark:border-gray-600 dark:placeholder-gray-400 dark:text-white dark:focus:ring-blue-500 dark:focus:border-blue-500" placeholder="1000" required>
          </div>
          <div class="mb-5">
            <label for="image" class="block mb-2 text-sm font-medium text-gray-900 dark:text-white">Your image</label>
            <input type="file" ref="fileInput" @change="handleFileUpload">
          </div>
          
          <button @click="switchToggle" class="text-white bg-gray-700 hover:bg-gray-800 focus:ring-4 focus:outline-none focus:ring-gray-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-gray-600 dark:hover:bg-gray-700 dark:focus:ring-gray-800 mr-5">Cancel</button>
          <button @click="confirmCreation" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Confirm</button>
        </div>
      </div>
    <div class="bg-gray-900 text-white min-h-screen">
      
      <div class="absolute top-4 left-4">
        <img src="@/assets/logo.png" alt="Logo" class="w-12 h-12">
      </div>
      <h1 class="text-4xl font-bold text-white mt-8 ml-4">Cash Manager</h1>
      <div v-if="role=='true'">
            <button @click="switchToggle" class="text-white mt-5  bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Create item</button>
          </div>
  
      <div class="container mx-auto mt-8">
        <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 xl:grid-cols-4 gap-8">
          <!-- Product Card -->
          <div v-for="product in products" :key="product.id" class="bg-gray-800 rounded-lg overflow-hidden shadow-lg transition-transform transform hover:scale-105">
            <div>
              <img v-if="role=='true'" @click="deleteProduct(product.id)" src="../assets/trash-bin.png" class="w-10 h-10 object-cover" style="position: absolute;top: 0;right:0">
              
            </div>
            <img :src="product.image" :alt="product.name" class="w-full h-56 object-cover">
            <div class="p-4">
              <h2 class="text-lg font-semibold mb-2">{{ product.name }}</h2>
              <p class="text-gray-300">{{ product.quantity }}</p>
              <div class="mt-4 flex items-center justify-between">
                <span class="text-yellow-500 font-bold">{{ product.price }}$</span>
                <button class="bg-blue-500 hover:bg-blue-700 text-white px-4 py-2 rounded" @click="addToCart(product.id,1)" >Add to Cart</button>
              </div>
            </div>
          </div>
          
          <!-- End Product Card -->
        </div>
        
      </div>
      <div class="text-4xl font-bold text-white">
        <button @click="toggleChoice"></button>
      </div>
      
    </div>
  </div>
  </template>
  
  <script>
  import {items} from "../services/products"
  import {panier} from "../services/panier"
  export default {
    name: 'ShoppingPage',
    data() {
      return {
        products: [
        ],
        display:false,
        role:"false",
        name:'',
        price:'',
        quantity:'',
        image:''
      };
    },async mounted(){
      this.role = localStorage.getItem("role")
      this.fetchData();
    },
    methods: {
      async fetchData(){
        this.products = (await items.getItems().catch((error) => {this.handleError(error)})).data
        console.log(this.products)
  },
      addToCart(id,quantity){
        panier.addItemToPanier(id,quantity)
      },
      switchToggle(){
        this.display=!this.display;
      },
      async confirmCreation(){
        await this.uploadImage()
        .then(async(imageName) =>{
          await items
          .addProducts(this.name,this.price,this.quantity,imageName)
          await this.fetchData()
          .catch((error)=> {this.handleError(error)})})
        this.switchToggle()
        
        
  },async deleteProduct(id){
      let answer =await items.removeProducts(id).catch((error)=> {this.handleError(error)}).then(await this.fetchData)
      
      return answer
    },
    handleError(error){
      if(error.response.status===403){
          items.message.handleUnconnected(error,this.$router)
    }
    },
    handleFileUpload(event) {
    this.selectedFile = event.target.files[0]; // Store selected file
  },
    async uploadImage() {
    
    let formData = new FormData();
    formData.append('file', this.selectedFile); // Append the file to FormData
    console.log(formData)
    return (await items.message.Axios.post('/api/images/upload', formData, {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    }
    
    )).data
    
  }
    
    
    },
  };
  </script>
  
  <style scoped>

  </style>
  