<template>
    <div>
      <div v-show="!showCreate" id="CONNECT" class="min-h-screen flex items-center justify-center">
        <div class="bg-blue-950 shadow-md rounded-lg p-8 text-white w-11/12 md:w-2/3 lg:w-1/2 xl:w-1/3 relative">
          <div class="absolute top-0 left-0 p-4">
            <img src="@/assets/logo.png" alt="Logo" class="w-10 h-10 mr-2">
          </div>
          <h2 class="text-2xl font-bold text-center mb-4">CASH MANAGER</h2>
          <h2 class="text-1xl font-bold text-center mb-4">LOG IN</h2>
          <h2 class="text-red-700 text-1xl font-bold text-center mb-4" id="error-login"></h2>
          <form>
            <div class="mb-4">
              <label for="username" class="block text-sm font-bold mb-2 text-center uppercase">Username</label>
              <input type="text" id="username" v-model="username" class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-green-500">
            </div>
            <div class="mb-4">
              <label for="password" class="block text-sm font-bold mb-2 text-center uppercase">Password</label>
              <input type="password" id="password" v-model="password" class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-green-500">
              <p class="text-sm text-gray-200 mt-2 text-center"><a href="#" class="underline" @click="logout">Forgot your password?</a> Click here to reset it.</p>
            </div>
            <div class="mb-4">
              <button @click.prevent="authenticate" class="w-full bg-gray-300 text-black p-2 rounded font-bold">LOG IN</button>
              <p class="text-sm text-gray-200 mt-2 text-center"><a href="#" class="underline" @click="toggleCreateSection">Don't have an account already?</a> Click here to create one.</p>
            </div>
          </form>
        </div>
      </div>
  
      <div v-show="showCreate" id="CREATE" class="min-h-screen flex items-center justify-center">
        <div class="bg-blue-950 shadow-md rounded-lg p-8 text-white w-11/12 md:w-2/3 lg:w-1/2 xl:w-1/3 relative">
          <div class="absolute top-0 left-0 p-4">
            <img src="@/assets/logo.png" alt="Logo" class="w-10 h-10 mr-2">
          </div>
          <h2 class="text-2xl font-bold text-center mb-4">CASH MANAGER</h2>
          <h2 class="text-1xl font-bold text-center mb-4">CREATE A NEW ACCOUNT</h2>
          <h2 class="text-red-700 text-1xl font-bold text-center mb-4" id="error-signup"></h2>
          <!-- Formulaire d'inscription -->
          <form>
            <div class="mb-4">
              <label for="newUsername" class="block text-sm font-bold mb-2 text-center uppercase">Username</label>
              <input type="text" id="newUsername" v-model="newUsername" class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-green-500">
            </div>
            <div class="mb-4">
              <label for="newEmail" class="block text-sm font-bold mb-2 text-center uppercase">Email</label>
              <input type="email" id="newEmail" v-model="newEmail" class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-green-500">
            </div>
            <div class="mb-4">
              <label for="newPassword" class="block text-sm font-bold mb-2 text-center uppercase">Password</label>
              <input type="password" id="newPassword" v-model="newPassword" class="w-full p-2 border border-gray-300 rounded focus:outline-none focus:border-green-500">
              <p class="text-sm text-gray-200 mt-2 text-center"><a href="#" class="underline" @click="toggleCreateSection">Already have an account ?</a> Click here to log in.</p>
            </div>
            <div class="mb-4">
              <button @click.prevent="register" class="w-full bg-gray-300 text-black p-2 rounded font-bold">REGISTER</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  import {authentication} from "../services/authentication"
  export default {
    name: 'AuthentificationForm',
    data() {
      return {
        username: '',
        password: '',
        newUsername: '',
        newEmail:'',
        newPassword: '',
        showCreate: false,
      };
    },async mounted(){
      if(localStorage.getItem("token")!=null){
        try{
          let response = await authentication.testAuthentication();
          if(response.status != 403){
            console.log(response)
            this.$router.push({name: "Home"});
          }
          
        }catch(e){
          localStorage.setItem("token",null)
          console.error(e)
        }
      }
      
    },
    methods: {
      async authenticate(){
        var status=await authentication.signin(this.username,this.password);
        switch(status){
          case 400:
            document.getElementById("error-login").textContent="Invalid username or password";
          break;
          case 200:
          document.getElementById("error-login").textContent="";
          this.$router.push({name: "Home"});
          break;
        }
      },
      async register() {
        let status = await authentication.signup(this.newUsername,this.newEmail,this.newPassword);
        console.log('Tentative d\'inscription avec :', this.newUsername, this.newEmail, this.newPassword,status);
        switch(status){
          case 400:
            document.getElementById("error-signup").textContent="Invalid username or Email";
          break;
          case 200:
          document.getElementById("error-signup").textContent="";
          this.$router.push({name: "Home"});
          break;
        }
        //api
      },
      toggleCreateSection() {
      this.showCreate = !this.showCreate;
    },
      logout(){
        authentication.logout()
      }
        },
  };
  
  </script>
  
  <style>

  </style>
  