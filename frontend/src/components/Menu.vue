<template>
    <div class="fixed bottom-0 w-full">
      <div class="p-2 w-full h-20 border-box bottom-0">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <div class="bg-slate-800 flex justify-around text-white w-full h-full rounded-xl">
          <router-link to="/home" class="below-menu-holder bg-blue-400 rounded-bl-xl rounded-tl-xl w-1/4 border-t-4 duration-700 flex justify-around">
            <span class="material-icons self-center">home</span>
          </router-link>
          <router-link to="/shopping" class="below-menu-holder w-1/4 flex duration-700 justify-around">
            <span class="material-icons self-center">book</span>
          </router-link>
          
          <router-link to="/QRCodeReader" class="below-menu-holder w-1/4 flex duration-700 justify-around">
            <img src="@/assets/qrcodemenu.png" alt="QR Code Logo" class="w-12 h-12 self-center">
          </router-link>
          
          <router-link to="/cart" class="below-menu-holder  w-1/4 flex duration-700 justify-around">
            <span class="material-icons self-center">shopping_cart</span>
          </router-link>
          <router-link to="/profile" class="below-menu-holder rounded-tr-xl rounded-br-xl w-1/4 flex duration-700 justify-around">
            <span class="material-icons self-center">chat_bubble</span>
          </router-link>
        </div>
      </div>
    </div>
  </template>
  
  <script>
  export default {
    name: 'AppMenu',
    data() {
      const holder = document.getElementsByClassName("below-menu-holder");
      var active = 0;
  
      function activate() {
        for (let x = 0; x < holder.length; x++) {
          if (x == active) {
            holder[x].classList.add("border-t-4", "bg-blue-400");
          } else {
            holder[x].classList.remove("border-t-4", "bg-blue-400");
          }
        }
      }
  
      for (let x = 0; x < holder.length; x++) {
        holder[x].addEventListener("click", () => {
          active = x;
          activate();
        });
      }
      return {};
    },
    methods: {},
  };
  </script>
  
  <style>

  </style>
  