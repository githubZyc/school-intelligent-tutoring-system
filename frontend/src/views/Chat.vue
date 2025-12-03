<template>
  <div class="chat">
    <div class="chat-header">
      <h2>AI Tutor Chat</h2>
      <div class="teaching-methods">
        <button 
          :class="{ active: selectedMethod === 'explain' }"
          @click="selectMethod('explain')"
        >
          Explain
        </button>
        <button 
          :class="{ active: selectedMethod === 'example' }"
          @click="selectMethod('example')"
        >
          Example
        </button>
        <button 
          :class="{ active: selectedMethod === 'exercise' }"
          @click="selectMethod('exercise')"
        >
          Exercise
        </button>
      </div>
    </div>
    
    <div class="chat-messages" ref="messagesContainer">
      <div 
        v-for="message in messages" 
        :key="message.id"
        :class="['message', message.sender]"
      >
        <div class="message-content">
          {{ message.text }}
        </div>
        <div class="message-time">
          {{ formatTime(message.timestamp) }}
        </div>
      </div>
    </div>
    
    <div class="chat-input">
      <form @submit.prevent="sendMessage">
        <input 
          v-model="newMessage"
          placeholder="Ask a question..."
          :disabled="isLoading"
        />
        <button 
          type="submit" 
          :disabled="!newMessage.trim() || isLoading"
          class="send-btn"
        >
          Send
        </button>
      </form>
      
      <div class="image-upload">
        <input 
          type="file" 
          id="image-upload" 
          accept="image/*" 
          @change="handleImageUpload"
        />
        <label for="image-upload" class="upload-btn">
          Upload Image
        </label>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'Chat',
  data() {
    return {
      newMessage: '',
      selectedMethod: 'explain',
      isLoading: false
    }
  },
  computed: {
    ...mapState(['messages'])
  },
  methods: {
    ...mapActions(['sendMessageToAI', 'uploadImage']),
    selectMethod(method) {
      this.selectedMethod = method
    },
    async sendMessage() {
      if (!this.newMessage.trim()) return
      
      try {
        this.isLoading = true
        await this.sendMessageToAI({
          message: this.newMessage,
          method: this.selectedMethod
        })
        this.newMessage = ''
      } catch (error) {
        console.error('Failed to send message:', error)
      } finally {
        this.isLoading = false
      }
    },
    async handleImageUpload(event) {
      const file = event.target.files[0]
      if (!file) return
      
      try {
        this.isLoading = true
        await this.uploadImage(file)
      } catch (error) {
        console.error('Failed to upload image:', error)
      } finally {
        this.isLoading = false
        // Reset file input
        event.target.value = ''
      }
    },
    formatTime(timestamp) {
      return new Date(timestamp).toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' })
    }
  },
  updated() {
    // Scroll to bottom of chat messages
    this.$nextTick(() => {
      const container = this.$refs.messagesContainer
      if (container) {
        container.scrollTop = container.scrollHeight
      }
    })
  }
}
</script>

<style scoped>
.chat {
  display: flex;
  flex-direction: column;
  height: calc(100vh - 100px);
  max-width: 800px;
  margin: 0 auto;
  padding: 1rem;
}

.chat-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid #eee;
}

.teaching-methods button {
  margin-left: 0.5rem;
  padding: 0.5rem 1rem;
  background: #f0f0f0;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: all 0.3s;
}

.teaching-methods button.active {
  background: #42b983;
  color: white;
}

.chat-messages {
  flex: 1;
  overflow-y: auto;
  padding: 1rem;
  background: #f9f9f9;
  border-radius: 8px;
  margin-bottom: 1rem;
}

.message {
  margin-bottom: 1rem;
  display: flex;
  flex-direction: column;
}

.message.user {
  align-items: flex-end;
}

.message.assistant {
  align-items: flex-start;
}

.message-content {
  max-width: 80%;
  padding: 0.75rem 1rem;
  border-radius: 18px;
  word-wrap: break-word;
}

.message.user .message-content {
  background: #42b983;
  color: white;
  border-bottom-right-radius: 4px;
}

.message.assistant .message-content {
  background: #e0e0e0;
  color: #333;
  border-bottom-left-radius: 4px;
}

.message-time {
  font-size: 0.75rem;
  color: #999;
  margin-top: 0.25rem;
}

.chat-input form {
  display: flex;
  margin-bottom: 1rem;
}

.chat-input input {
  flex: 1;
  padding: 0.75rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-right: 0.5rem;
}

.send-btn {
  padding: 0.75rem 1.5rem;
  background: #42b983;
  color: white;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s;
}

.send-btn:disabled {
  background: #ccc;
  cursor: not-allowed;
}

.image-upload {
  display: flex;
  align-items: center;
}

#image-upload {
  display: none;
}

.upload-btn {
  padding: 0.5rem 1rem;
  background: #f0f0f0;
  border: 1px solid #ddd;
  border-radius: 4px;
  cursor: pointer;
  transition: background 0.3s;
}

.upload-btn:hover {
  background: #e0e0e0;
}
</style>