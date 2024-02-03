<template>  
<div>
  <div class="absolute top-0 left-0 p-4">
      <img src="@/assets/logo.png" alt="Logo" class="w-12 h-12 mr-2">
  </div>
    <h1 class="text-2xl font-bold text-white mt-8 ml-16">Cash Manager</h1>
  
    <div class="mt-8">
      <h3 class="text-xl font-semibold mb-4">Order History</h3>
      <div class="flex flex-col space-y-4">

        
        <div v-for="index in data" :key="index.id"  class="bg-gray-200 p-4 rounded-lg">
          <p class="text-gray-800"><span class="font-semibold">Order ID:</span> #{{index.id}}</p>
          <div v-for="i in index.productId.length" :key="i" style="display: flex;justify-content: space-between;">
            <p  class="text-gray-600">
              <span class="font-semibold">Item:</span>
               {{  (names.get(index.productId[i-1]))  }} * {{ index.productQuantity[i-1] }} 
               </p><span style="color:black">{{ index.productPrices[i-1] }}$</span></div>
          <div class="text-gray-600" style="display:flex;justify-content: space-between;"><span class="font-semibold">Total:</span> <span>{{index.totalPrices}}$</span></div>
        </div>

        

        

      </div>
    </div>
  </div>
</template>

<script>
import {transaction} from "../services/transaction"
import {items} from "../services/products"
export default {
name: 'TransactionPage',
data() {
return {
  data:[

  ],
  panierData:[

  ],
  names: new Map()
  
};
},async mounted(){
    await this.getTransactions();
},
methods: {
  async getTransactions(){
    this.names = new Map()
    this.data = (await transaction.getAllTransaction()).data
    for(let i=0;i<this.data.length;i++){
      for(let j=0;j<this.data[i].productId.length;j++){
        if(!this.names.has(this.data[i].productId[j])){
          let data = (await items.getItemsById(this.data[i].productId[j])).data;
          console.log(data)
          this.names.set(this.data[i].productId[j],data.name)
        }
      }
    }
  }
},
};
</script>

<style scoped>

</style>
