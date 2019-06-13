from django.forms.models import model_to_dict
from django.http import JsonResponse
from django.shortcuts import render
from django.views.decorators.csrf import csrf_exempt
from .models import Chat, Stuff
import json
import time

# Create your views here.
@csrf_exempt
def createStuff(request):
    body = json.loads(request.body.decode('utf-8'))
    body['status'] = 'selling'
    curr = str(int(time.time()))
    body['created_at'] = curr
    body['updated_at'] = curr
    Stuff.objects.create(**body)
    return JsonResponse({'msg': 'ok'})

@csrf_exempt
def getStuffs(request):
    body = json.loads(request.body.decode('utf-8'))
    stuffs = Stuff.objects.filter(owner=body['owner'])
    stuffs = list(stuffs.values())
    return JsonResponse({'allStuffs': stuffs})

@csrf_exempt
def delStuff(request):
    body = json.loads(request.body.decode('utf-8'))
    _id = body['id']
    Stuff.objects.get(id=_id).delete()
    return JsonResponse({'msg': 'ok'})

@csrf_exempt
def setStuffBuying(request):
    body = json.loads(request.body.decode('utf-8'))
    _id = body['id']
    curr = str(int(time.time()))
    Stuff.objects.filter(id=_id).update(status='buying', updated_at=curr)
    return JsonResponse({'msg': 'ok'})

@csrf_exempt
def setStuffSoldout(request):
    body = json.loads(request.body.decode('utf-8'))
    _id = body['id']
    curr = str(int(time.time()))
    Stuff.objects.filter(id=_id).update(status='soldout', updated_at=curr)
    return JsonResponse({'msg': 'ok'})

@csrf_exempt
def sendMsg(request):
    body = json.loads(request.body.decode('utf-8'))
    curr = str(int(time.time()))
    body['time'] = curr
    Chat.objects.create(**body)
    return JsonResponse({'msg': 'ok'})

@csrf_exempt
def getMsgs(request):
    chats = Chat.objects.all()
    chats = list(chats.values())
    return JsonResponse({'allMsgs': chats})