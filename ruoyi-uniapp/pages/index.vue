<template>
	<view class="content">
		<image class="logo" src="@/static/logo.png"></image>
		<view class="text-area">
			<text class="title">你好 {{brand}}用户</text>
		</view>
		<button @click="phone()">拨打电话</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				brand: ''
			}
		},
		onLoad: function() {
			this.getbrand()
			this.getBattery()
		},
		methods: {
			getBattery(){
				uni.getBatteryInfo({
				  success: (res) => {
				    console.log(res)
				  }
				})
			},
			getbrand() {
				let that = this
				uni.getSystemInfo({
					success: function(res) {
						that.brand = res.brand
						console.log(res);
						console.log(res.brand)
					}
				})
			},
			phone(){
				console.log('拨打电话');
				uni.makePhoneCall({
					phoneNumber: '15098282377' //仅为示例
				});
			}
		}
	}
</script>

<style>
	.content {
		display: flex;
		flex-direction: column;
		align-items: center;
		justify-content: center;
	}

	.logo {
		height: 200rpx;
		width: 200rpx;
		margin-top: 200rpx;
		margin-left: auto;
		margin-right: auto;
		margin-bottom: 50rpx;
	}

	.text-area {
		display: flex;
		justify-content: center;
	}

	.title {
		font-size: 36rpx;
		color: #8f8f94;
	}
</style>