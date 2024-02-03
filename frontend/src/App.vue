<script>
import Menu from "./components/Menu.vue"

export default {
  name: 'App',
  components: {
    Menu
  }
};
</script>
<template>
  <div>
  <router-view></router-view>
  <Menu v-if="$route.name !== 'Authentification'"/>
</div>
</template>