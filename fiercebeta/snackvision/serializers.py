from rest_framework import serializers
from snackvision.models import Marker

class SnackvisionSerializer(serializers.ModelSerializer):
    class Meta:
        model = Marker
        fields = ('id','lat','lon','type','time_placed','placed_by')
