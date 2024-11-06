import picamera
from time import sleep

camera = picamera.PiCamera()
camera.resolution = (1024, 768)
camera.brightness = 60

camera.start_preview()
camera.annotate = 'Hello'
sleep(5)

camera.capture('img1.jpg')
camera.stop_preview()
