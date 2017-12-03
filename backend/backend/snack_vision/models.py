from django.db import models
from django.contrib.auth.models import User
# Create your models here.

# Max Lemos and Billy Crossman
# This model helps define the tables in our SQL DB
# new_Node is the model for a an item that will be placed on the map.

class new_Node(models.Model):
        '''
        new_Node model
        '''
        name = models.CharField(max_length=40)
        loc_long = models.DecimalField(max_digits=9, decimal_places=6)
        loc_lat = models.DecimalField(max_digits=9, decimal_places=6)
        node_type = models.CharField(max_length=30)
        create_time = models.DateTimeField(auto_now_add=True)
        placed_by = models.ForeignKey(User, on_delete=models.CASCADE)
