<template>
  <div class="bg-gray-900 text-white min-h-screen">
    <div class="absolute top-4 left-4">
      <img src="@/assets/logo.png" alt="Logo" class="w-12 h-12">
    </div>
    <h1 class="text-4xl font-bold text-white mt-8 ml-4">Cash Manager</h1>


    <div class="grid grid-cols-2 gap-4 mt-8 mx-4">
      <router-link to="/QrCodeReader" class="p-4 rounded-md shadow bg-blue-500 flex items-center justify-center">
        <img src="@/assets/qrcode.png" alt="Scan QR Code" class="w-12 h-12 mr-2" />
        <span class="text-xl font-semibold">SCAN HERE</span>
      </router-link> 
      <router-link to="/QrCodeReader" class="p-4 rounded-md shadow bg-blue-500 flex items-center justify-center">
        <img src="@/assets/qrcode.png" alt="Scan QR Code" class="w-12 h-12 mr-2" />
        <span class="text-xl font-semibold">NFC</span>
      </router-link> 
      <router-link to="/profile" class="p-4 rounded-md shadow bg-yellow-500 flex items-center justify-center">
        <img src="@/assets/carte_bancaire.png" alt="Case 4" class="w-12 h-12 mr-2" />
        <span class="text-xl font-semibold">ADD CARD</span>
      </router-link>
    </div>
    <div v-if="!isPaying&&!isPaymentDone" class="grid grid-cols-1 gap-4 mt-8 mx-4">
      
      <select  v-model="selected">
        <option v-for="item in creditCards" :key="item.number" :value="item.id" > {{ item.number}}</option>
      </select>
      <button @click="payItems(selected)" class="text-white bg-blue-700 hover:bg-blue-800 focus:ring-4 focus:outline-none focus:ring-blue-300 font-medium rounded-lg text-sm w-full sm:w-auto px-5 py-2.5 text-center dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">Pay</button>
      
    </div>
    <div v-if="isPaying&&isPaymentDone" class="grid grid-cols-1 gap-4 mt-8 mx-4">
      
      <p>Payment done</p>
    </div>
    <div v-if="isPaying&&!isPaymentDone" class="grid grid-cols-1 gap-4 mt-8 mx-4">
      
      <p>Payment processing</p>
    </div>
  </div>
</template>

<script>
import {payment} from "../services/payment.js"
import {transaction} from "../services/transaction.js"
import { HttpStatusCode } from 'axios';
export default {
  name: 'PaymentPage',
  data() {
    return {
      creditCards:[

      ],
      selected:null,
      isPaying:false,
      isPaymentDone:false,

    };
  },
  methods: {
    async getCreditCard(){
      this.creditCards = (await payment.getRegisteredCards()).data;
      for(let i = 0 ; i< this.creditCards.length ; i++){
        if(this.creditCards[i].pending){
          console.log("hey")
          this.isPaying = true;
          this.isPaymentDone = false;
          this.checkPayment(this.creditCards[i].pendingId)
        }
      }
      console.log(this.creditCards)
    },
  async payItems(id){
      if(id != null){
        
        let response= await transaction.payWithCard(id)
        this.isPaying = true;
        this.isPaymentDone = false;
        this.checkPayment(response.data)
      }else{
        console.log("no")
      }
    },async checkPayment(id){
      let response = await transaction.checkPayment(id);
      if(response.status == HttpStatusCode.Accepted){
        await this.sleep(2000)
        return await this.checkPayment(id);
      }
      this.isPaying=true;
      this.isPaymentDone=true;
    },sleep(ms) {
        return new Promise(resolve => setTimeout(resolve, ms));
      }
  },
  async mounted(){
    await this.getCreditCard()
  },
  

};
</script>

<style scoped>

</style>
