<template>
  <div>
    <p v-if="!nfcSupported">NFC not supported</p>
    <p v-else-if="nfcSupported && !nfcReading">Place NFC tag near the device</p>
    <p v-else>Reading NFC tag...</p>
    <p>{{ data }}</p>
  </div>
</template>

<script>
export default {
  data() {
    return {
      nfcSupported: false,
      nfcReading: false,
      data: "no data read for now "
    };
  },
  mounted() {
    this.checkNFCSupport();
  },
  methods: {
    async checkNFCSupport() {
      if ('NDEFReader' in window) {
        try {
          const permissionStatus = await navigator.permissions.query({ name: 'nfc' });
          if (permissionStatus.state === 'granted') {
            this.nfcSupported = true;
            this.data = "data true"
            await this.startReading();
          } else if (permissionStatus.state === 'prompt') {
            await permissionStatus.onchange;
            this.nfcSupported = true;
            this.data = "data true"
            await this.startReading();
          } else {
            // Permission denied, handle it accordingly
            console.log('NFC permission denied');
          }
        } catch (error) {
          console.error('Error checking NFC support:', error);
        }
      }else{
        console.log("no nfc to accept")
        this.data = "no nfc to accept"
      }
    },
    async startReading() {
      if (this.nfcSupported) {
        const reader = new this.NDEFReader();
        try {
           await reader.scan();
          this.nfcReading = true;
          reader.onreading = ({ message, serialNumber }) => {
            console.log('Tag serial number:', serialNumber);
            this.data= message
            // Handle tag data
          };
        } catch (error) {
          console.error('Error:', error);
        }
      }
    },
  },
};
</script>