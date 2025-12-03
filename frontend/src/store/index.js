import { createStore } from 'vuex'
import axios from 'axios'

// Set base URL for API requests
axios.defaults.baseURL = 'http://localhost:8080'

const store = createStore({
  state: {
    user: null,
    token: localStorage.getItem('token') || null,
    isLoggedIn: !!localStorage.getItem('token'),
    messages: [],
    learningRecords: []
  },
  
  mutations: {
    SET_USER(state, user) {
      state.user = user
    },
    
    SET_TOKEN(state, token) {
      state.token = token
      state.isLoggedIn = !!token
      if (token) {
        localStorage.setItem('token', token)
        axios.defaults.headers.common['Authorization'] = `Bearer ${token}`
      } else {
        localStorage.removeItem('token')
        delete axios.defaults.headers.common['Authorization']
      }
    },
    
    LOGOUT(state) {
      state.user = null
      state.token = null
      state.isLoggedIn = false
      localStorage.removeItem('token')
      delete axios.defaults.headers.common['Authorization']
    },
    
    ADD_MESSAGE(state, message) {
      state.messages.push(message)
    },
    
    SET_MESSAGES(state, messages) {
      state.messages = messages
    },
    
    SET_LEARNING_RECORDS(state, records) {
      state.learningRecords = records
    }
  },
  
  actions: {
    async register({ commit }, userData) {
      try {
        const response = await axios.post('/api/auth/register', userData)
        const { token, user } = response.data
        commit('SET_TOKEN', token)
        commit('SET_USER', user)
        return response.data
      } catch (error) {
        throw error.response.data
      }
    },
    
    async login({ commit }, credentials) {
      try {
        const response = await axios.post('/api/auth/login', credentials)
        const { token, user } = response.data
        commit('SET_TOKEN', token)
        commit('SET_USER', user)
        return response.data
      } catch (error) {
        throw error.response.data
      }
    },
    
    logout({ commit }) {
      commit('LOGOUT')
    },
    
    async sendMessageToAI({ commit, state }, { message, method }) {
      try {
        // Add user message to chat
        const userMessage = {
          id: Date.now(),
          text: message,
          sender: 'user',
          timestamp: new Date()
        }
        commit('ADD_MESSAGE', userMessage)
        
        // Send request to backend
        let response
        if (method) {
          response = await axios.post('/api/question/ask-with-method', {
            question: message,
            method: method
          })
        } else {
          response = await axios.post('/api/question/ask', {
            question: message
          })
        }
        
        // Add AI response to chat
        const aiMessage = {
          id: Date.now() + 1,
          text: response.data.answer,
          sender: 'assistant',
          timestamp: new Date()
        }
        commit('ADD_MESSAGE', aiMessage)
        
        return response.data
      } catch (error) {
        throw error.response.data
      }
    },
    
    async uploadImage({ commit }, file) {
      try {
        const formData = new FormData()
        formData.append('image', file)
        formData.append('question', 'Analyze this image')
        
        const response = await axios.post('/api/question/ask-with-image', formData, {
          headers: {
            'Content-Type': 'multipart/form-data'
          }
        })
        
        // Add user message (image) to chat
        const userMessage = {
          id: Date.now(),
          text: 'Uploaded an image',
          sender: 'user',
          timestamp: new Date()
        }
        commit('ADD_MESSAGE', userMessage)
        
        // Add AI response to chat
        const aiMessage = {
          id: Date.now() + 1,
          text: response.data.answer,
          sender: 'assistant',
          timestamp: new Date()
        }
        commit('ADD_MESSAGE', aiMessage)
        
        return response.data
      } catch (error) {
        throw error.response.data
      }
    }
  },
  
  getters: {
    isAuthenticated: state => state.isLoggedIn
  }
})

export default store