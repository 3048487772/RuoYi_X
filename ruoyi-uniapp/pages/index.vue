<template>
	<view class="content">
		<image class="logo" src="@/static/logo.png"></image>
		<view class="text-area">
			<text class="title">你好 {{brand}}用户</text>
		</view>
		<button @click="phone()">拨打电话</button>
		<button @click="onlineLocation()">更新实时位置</button>
		<text>经度：{{location.lng}},维度：{{location.lat}}</text>
		<button @click="phone()">拨打电话</button>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				brand: '',
				location: {
					lat:0,
					lng:0
				}
			}
		},
		onLoad: function() {
			this.getbrand()
			this.getBattery()
		},
		methods: {
			onlineLocation() {
				this.brand='小米'
				let that = this
				uni.startLocationUpdate({
					success: res => console.log('开启小程序接收位置消息成功'),
					fail: err => console.error('开启小程序接收位置消息失败：', err),
					complete: msg => console.log('调用开启小程序接收位置消息 API 完成')
				});
				uni.onLocationChange(function(res) {
					that.location.lat = res.latitude
					that.location.lng = res.longitude
					console.log('纬度：' + res.latitude);
					console.log('经度：' + res.longitude);
					console.log(that.location);
				});
			},
			getBattery() {
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
			phone() {
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