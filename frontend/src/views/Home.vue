<template>
  <div class="home">
    <h2>Welcome to the School Intelligent Tutoring System</h2>
    <p>This system provides personalized learning assistance for elementary school students.</p>
    
    <div class="features">
      <div class="feature-card">
        <h3>Ask Questions</h3>
        <p>Get explanations, examples, and exercises for any topic.</p>
      </div>
      
      <div class="feature-card">
        <h3>Image Recognition</h3>
        <p>Upload images of math problems for instant help.</p>
      </div>
      
      <div class="feature-card">
        <h3>Learning Tracking</h3>
        <p>Monitor your progress and identify areas for improvement.</p>
      </div>
    </div>
    
    <div class="cta" v-if="!isLoggedIn">
      <router-link to="/register" class="btn">Get Started</router-link>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'Home',
  computed: {
    ...mapState(['isLoggedIn'])
  }
}
</script>

<style scoped>
.home {
  max-width: 800px;
  margin: 0 auto;
}

.features {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
  gap: 2rem;
  margin: 2rem 0;
}

.feature-card {
  background: #f5f5f5;
  padding: 1.5rem;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0,0,0,.1);
}

.feature-card h3 {
  color: #42b983;
  margin-top: 0;
}

.cta {
  text-align: center;
  margin: 2rem 0;
}

.btn {
  display: inline-block;
  padding: 0.75rem 1.5rem;
  background-color: #42b983;
  color: white;
  text-decoration: none;
  border-radius: 4px;
  font-weight: bold;
  transition: background-color 0.3s;
}

.btn:hover {
  background-color: #359c6d;
}
</style>