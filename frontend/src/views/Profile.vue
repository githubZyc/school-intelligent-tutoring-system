<template>
  <div class="profile">
    <h2>User Profile</h2>
    <div class="profile-info" v-if="user">
      <p><strong>Username:</strong> {{ user.username }}</p>
      <p><strong>Email:</strong> {{ user.email }}</p>
      <p><strong>Role:</strong> {{ user.role }}</p>
      <p><strong>Member since:</strong> {{ formatDate(user.createdAt) }}</p>
    </div>
    
    <div class="learning-progress" v-if="learningRecords.length > 0">
      <h3>Learning Progress</h3>
      <div class="progress-item" v-for="record in learningRecords" :key="record.id">
        <h4>{{ record.topic }}</h4>
        <p>Attempts: {{ record.attempts }} | Success: {{ record.successCount }}</p>
        <p>Last attempt: {{ formatDate(record.lastAttemptAt) }}</p>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState } from 'vuex'

export default {
  name: 'Profile',
  computed: {
    ...mapState(['user', 'learningRecords'])
  },
  methods: {
    formatDate(dateString) {
      if (!dateString) return 'N/A'
      const date = new Date(dateString)
      return date.toLocaleDateString()
    }
  }
}
</script>

<style scoped>
.profile {
  max-width: 800px;
  margin: 0 auto;
  padding: 2rem;
}

.profile-info {
  background: #f9f9f9;
  padding: 1.5rem;
  border-radius: 8px;
  margin-bottom: 2rem;
}

.profile-info p {
  margin: 0.5rem 0;
}

.learning-progress h3 {
  color: #42b983;
  border-bottom: 2px solid #42b983;
  padding-bottom: 0.5rem;
}

.progress-item {
  background: #f5f5f5;
  padding: 1rem;
  margin: 1rem 0;
  border-radius: 4px;
}

.progress-item h4 {
  margin-top: 0;
  color: #359c6d;
}
</style>