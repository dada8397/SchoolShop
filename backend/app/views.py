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
    body['img_url'] = json.dumps(body['img_url'])
    body['created_at'] = curr
    body['updated_at'] = curr
    Stuff.objects.create(**body)
    return JsonResponse({'msg': 'ok'})

@csrf_exempt
def getStuffs(request):
    # body = json.loads(request.body.decode('utf-8'))
    stuffs = Stuff.objects.all()
    stuffs = list(stuffs.values())
    for stuff in stuffs:
        stuff['img_url'] = json.loads(stuff['img_url'])
    return JsonResponse({'allStuffs': stuffs})

@csrf_exempt
def delStuff(request):
    body = json.loads(request.body.decode('utf-8'))
    _id = body['id']
    Stuff.objects.get(id=_id).delete()
    return JsonResponse({'msg': 'ok'})

@csrf_exempt
def setStuffSelling(request):
    body = json.loads(request.body.decode('utf-8'))
    _id = body['id']
    curr = str(int(time.time()))
    Stuff.objects.filter(id=_id).update(status='selling', updated_at=curr)
    return JsonResponse({'msg': 'ok'})

@csrf_exempt
def setStuffBuying(request):
    body = json.loads(request.body.decode('utf-8'))
    _id = body['id']
    buyer = body['buyer']
    curr = str(int(time.time()))
    Stuff.objects.filter(id=_id).update(buyer=buyer, status='buying', updated_at=curr)
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