<script setup lang="ts">
import {ref} from 'vue'
import {useAuthStore} from '@/stores/auth'
import axiosInstance from '@/axiosInstance'
import {useRouter} from 'vue-router'

const authStore = useAuthStore()
const router = useRouter()

const fullName = ref('')
const username = ref('')
const password = ref('')
const isError = ref(false)
const isLoading = ref(false)
const showMessage = ref(false)
const message = ref('')

const delay = (ms: number) => new Promise(resolve => setTimeout(resolve, ms))

const register = async () => {
	if (isLoading.value) return
	
	isLoading.value = true
	showMessage.value = false
	isError.value = false
	
	try {
		await axiosInstance.post('/user/register', {
			fullName: fullName.value.trim(),
			username: username.value.trim(),
			password: password.value.trim(),
		})
		
		await delay(2000)
		
		const response = await axiosInstance.post('/user/login', {
			username: username.value.trim(),
			password: password.value.trim(),
		})
		
		const {accessToken, refreshToken} = response.data
		authStore.setTokens(accessToken, refreshToken)
		
		isError.value = false
		message.value = `${fullName.value} registered successfully!`
		showMessage.value = true
	} catch (error: any) {
		isError.value = true
		message.value = error?.response?.data?.message || 'Registration failed.'
		showMessage.value = true
	} finally {
		isLoading.value = false
	}
}

const login = () => {
	router.push({name: 'login'})
}
</script>

<template>
	<main class="h-screen w-screen flex items-center justify-center">
		<form
				@submit.prevent="register"
				class="rounded-lg p-8 border-1 border-pink-700 min-w-[400px]">
			
			<h3 class="text-2xl text-pink-500">Create Account</h3>
			<p class="mb-5 text-md text-gray-500">Track your mood. Are you cute?</p>
			
			<div class="flex flex-col mb-3">
				<label for="fullName" class="mb-1 text-gray-700">Full Name:</label>
				<input type="text"
				       v-model="fullName"
				       name="fullName"
				       placeholder="Enter your name"
				       class="border border-pink-300 bg-pink-100 py-2 px-4 rounded-lg focus:outline-none focus:ring-2
				            focus:ring-pink-400 focus:border-pink-500 transition-all duration-200">
			</div>
			
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
			
			<p v-if="isError && showMessage"
			   class="w-full text-center bg-red-100 text-red-500 border border-red-300 rounded-lg py-2 px-4 mb-4 transition-all duration-300">
				{{ message }}
			</p>
			<p v-if="!isError && showMessage"
			   class="w-full text-center bg-green-100 text-green-700 border border-green-300 rounded-lg py-2 px-4 mb-4 transition-all duration-300">
				{{ message }}
			</p>
			
			<button
					type="submit"
					:disabled="isLoading"
					class="w-full bg-pink-500 hover:bg-pink-600 text-pink-100 hover:text-pink-50 text-lg py-2 px-4 rounded-lg
			        cursor-pointer mb-3 mt-3 flex items-center justify-center gap-2">
				<span v-if="isLoading"
				      class="w-5 h-5 border-2 border-t-2 border-white border-t-transparent rounded-full animate-spin"></span>
				{{ isLoading ? 'Registering...' : 'Register' }}
			</button>
			
			<button
					@click="login"
					type="button"
					class="w-full bg-pink-50 hover:bg-pink-100 text-pink-500 hover:text-pink-600 text-lg py-2 px-4 rounded-lg
			        cursor-pointer mb-2">
				Already Have an Account
			</button>
		</form>
	</main>
</template>