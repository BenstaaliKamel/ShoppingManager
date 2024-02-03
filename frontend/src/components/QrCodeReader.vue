<template>
  <div class="bg-gray-50 px-8">
    <p>{{ error }}</p>
    <p>{{ decodedString }}</p>
    <qrcode-stream @init="onInit" @decode="onDecode"></qrcode-stream>
  </div>
</template>

<script>
import { QrcodeStream } from 'vue3-qrcode-reader';
import { transaction} from "../services/transaction"
export default {
  data() {
    return {
      error: '',
      decodedString: '',
    };
  },
  components: {
    QrcodeStream,
  },
  methods: {
    async onInit(promise) {
      try {
        await promise;
        // Successfully initialized
      } catch (error) {
        if (error.name === 'NotAllowedError') {
          this.error = "User denied camera access permission";
        } else if (error.name === 'NotFoundError') {
          this.error = "No suitable camera device installed";
        } else if (error.name === 'NoSupportedError') {
          this.error = "Page is not served over HTTPS (or localhost)";
        } else if (error.name === 'NotReadableError') {
          this.error = "Maybe the camera is already in use";
        } else if (error.name === 'OverconstrainedError') {
          this.error = "Did you request the front camera although there is none?";
        } else if (error.name === 'StreamApiNotSupportedError') {
          this.error = "Browser seems to be lacking features";
        }
      } finally {
        // Code à exécuter après l'initialisation
      }
    },

    async onDecode(decodedString) {
      let data = decodedString.split(" ")
      console.log(data)
      if(data.length==3){
        console.log(await transaction.payWithCheque(data[0],data[1]))
      }
      //window.location.replace(decodedString);
    }
  },
};
</script>

<style scoped>
/* Styles spécifiques au composant */
</style>
