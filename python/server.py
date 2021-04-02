
from playsound import playsound
from win10toast import ToastNotifier
import eventlet
import socketio
import os
import sys

toaster = ToastNotifier()
sio = socketio.Server()
app = socketio.WSGIApp(sio, static_files={
    '/': {'content_type': 'text', 'filename': 'index.html'}
})
calling = False

    
def call():
    toaster.show_toast("Hi, Logesh","Your brother is calling you!!!",icon_path=None,duration=10,threaded=True)
    playsound('telephone-ring-04.wav')
    global calling
    calling = False

def food():
    playsound('whistling_sms.mp3')
           
@sio.event
def connect(sid, environ):
    print('connect',sid)
  
    
@sio.on('message')
def message(sid, message):
    if message == 'call':
        sio.emit('message', 'we received ur call')  
        global calling
        if calling ==  False:
            calling = True
            call()
        else:
            sio.emit('message', 'please wait..')  
    if message == 'food':
        food()
@sio.event
def disconnect(sid):
    print('disconnect ', sid)

if __name__ == '__main__':
    eventlet.wsgi.server(eventlet.listen(('192.168.1.3', 8000)), app)






    