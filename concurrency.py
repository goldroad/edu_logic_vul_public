import requests
import threading

# 请求的URL
url = "http://localhost:8081/edu/api/coupon/exchange"

# 请求的头部信息
headers = {
    "Content-Type": "application/json",
    "Accept": "*/*",
    "Origin": "http://localhost:8081",
    "Sec-Fetch-Site": "same-origin",
    "Sec-Fetch-Mode": "cors",
    "Sec-Fetch-Dest": "empty",
    "Referer": "http://localhost:8081/edu/student/coupons",
    "Accept-Encoding": "gzip, deflate, br",
    "Accept-Language": "zh-CN,zh;q=0.9",
    "Cookie": "JSESSIONID=6F1DB0DD42E5B45130EB585BE2E1FF11",
    "Connection": "close",
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.6367.60 Safari/537.36"
}

# 请求的数据
data = '{"code":"VIP100"}'

# 并发请求的线程函数
def send_request():
    try:
        response = requests.post(url, headers=headers, data=data)
        print(response.status_code, response.text)
    except Exception as e:
        print(f"Error: {e}")

# 线程列表
threads = []

# 并发数量
concurrent_requests = 100

# 创建并启动线程
for _ in range(concurrent_requests):
    thread = threading.Thread(target=send_request)
    threads.append(thread)
    thread.start()

# 等待所有线程完成
for thread in threads:
    thread.join()