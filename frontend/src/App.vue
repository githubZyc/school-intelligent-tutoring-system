<template>
  <div id="app">
    <header>
      <h1>School Intelligent Tutoring System</h1>
      <nav>
        <router-link to="/">Home</router-link>
        <router-link to="/chat" v-if="isLoggedIn">Chat</router-link>
        <router-link to="/login" v-if="!isLoggedIn">Login</router-link>
        <router-link to="/register" v-if="!isLoggedIn">Register</router-link>
        <router-link to="/profile" v-if="isLoggedIn">Profile</router-link>
        <button @click="handleLogout" v-if="isLoggedIn">Logout</button>
      </nav>
    </header>
    <main>
      <router-view />
    </main>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'App',
  computed: {
    ...mapState(['isLoggedIn'])
  },
  methods: {
    ...mapActions(['logout']),
    handleLogout() {
      this.logout()
      this.$router.push('/')
    }
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}

header {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  background-color: #42b983;
  padding: 1rem;
  box-shadow: 0 2px 4px rgba(0,0,0,.1);
  z-index: 100;
}

header h1 {
  color: white;
  margin: 0;
}

nav {
  margin-top: 0.5rem;
}

nav a, nav button {
  color: white;
  margin-right: 1rem;
  text-decoration: none;
  background: none;
  border: none;
  cursor: pointer;
  padding: 0.5rem;
  border-radius: 4px;
  transition: background 0.3s;
}

nav a:hover, nav button:hover {
  background: rgba(255, 255, 255, 0.2);
}

nav a.router-link-exact-active {
  background: rgba(255, 255, 255, 0.3);
}

main {
  margin-top: 100px;
  padding: 1rem;
  min-height: calc(100vh - 100px);
}
</style>