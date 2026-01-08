<script setup lang="ts">
import {ref} from 'vue'
import {useAuthStore} from '@/stores/auth'
import axiosInstance from '@/axiosInstance'

const authStore = useAuthStore()

const username = ref('')
const password = ref('')
const isError = ref(false)
const isLoading = ref(false)
const showMessage = ref(false)
const message = ref('')

const delay = (ms: number) => new Promise(resolve => setTimeout(resolve, ms))

const login = async () => {
	if (isLoading.value) return
	
	isLoading.value = true
	showMessage.value = false
	isError.value = false
	
	try {
		const [response] = await Promise.all([axiosInstance.post('/user/login', {
			username: username.value.trim(),
			password: password.value.trim(),
		}),
			delay(2000)
		])
		
		const {accessToken, refreshToken} = response.data
		authStore.setTokens(accessToken, refreshToken)
		
		isError.value = false
		message.value = 'Login successful!'
		showMessage.value = true
	} catch (error: any) {
		isError.value = true
		message.value = error?.response?.data?.message || 'Invalid credentials'
		showMessage.value = true
	} finally {
		isLoading.value = false
	}
}
</script>

<template>
	<main class="h-screen w-screen flex items-center justify-center">
		<form
				@submit.prevent="login"
				class="rounded-lg p-8 border-1 border-pink-700 min-w-[340px]">
			
			<h3 class="text-2xl text-pink-500">Mood Tracker</h3>
			<p class="mb-5 text-md text-gray-500">What's your mood today?</p>
			
			<div class="flex flex-col mb-3">
				<label for="username" class="mb-1 text-gray-700">Username:</label>
				<input type="text"
				       v-model="username"
				       name="username"
				       placeholder="Enter username"
				       class="border border-pink-300 bg-pink-100 py-2 px-4 rounded-lg focus:outline-none focus:ring-2
				            focus:ring-pink-400 focus:border-pink-500 transition-all duration-200">
			</div>
			
			<div class="flex flex-col mb-5">
				<label for="password" class="mb-1 text-gray-700">Password:</label>
				<input type="password"
				       v-model="password"
				       name="password"
				       placeholder="Enter password"
				       class="border border-pink-300 bg-pink-100 py-2 px-4 rounded-lg focus:outline-none focus:ring-2
											focus:ring-pink-400 focus:border-pink-500 transition-all duration-200">
			</div>
			
			<button
					type="submit"
					:disabled="isLoading"
					class="w-full bg-pink-500 hover:bg-pink-600 text-pink-100 hover:text-pink-50 text-lg py-2 px-4 rounded-lg
			        cursor-pointer mb-2 flex items-center justify-center gap-2">
				<span v-if="isLoading"
				      class="w-5 h-5 border-2 border-t-2 border-white border-t-transparent rounded-full animate-spin"></span>
				{{ isLoading ? 'Logging in...' : 'Login' }}
			</button>
			
			<p class="w-full text-center text-red-500" v-if="isError && showMessage">{{ message }}</p>
			<p class="w-full text-center text-green-500" v-if="!isError && showMessage">{{ message }}</p>
		</form>
	</main>
</template>