from django.db import models

# Create your models here.
class Marker(models.Model):
    lat = models.FloatField()
    lon = models.FloatField()
    type = models.CharField(max_length=10, default='garden')
    time_placed = models.DateTimeField(auto_now_add=True)
    placed_by = models.IntegerField()

