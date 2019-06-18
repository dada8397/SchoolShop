from django.db import models

# Create your models here.
class Stuff(models.Model):
    name = models.TextField(blank=True)
    owner = models.TextField(blank=True)
    buyer = models.TextField(blank=True)    
    description = models.TextField(blank=True)
    img_url = models.TextField(blank=True)
    price = models.TextField(blank=True)
    status = models.TextField(blank=True)
    created_at = models.TextField(blank=True)
    updated_at = models.TextField(blank=True)

class Chat(models.Model):
    src = models.TextField(blank=True)
    dst = models.TextField(blank=True)
    stuff_id = models.TextField(blank=True)
    content = models.TextField(blank=True)
    time = models.TextField(blank=True)
