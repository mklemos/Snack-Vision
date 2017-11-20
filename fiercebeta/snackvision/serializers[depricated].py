from rest_framework import serializers
from snackvision.models import Marker

class SnackvisionSerializer(serializers.Serializer):
    id = serializers.IntegerField(read_only=True)
    lat = serializers.FloatField()
    lon = serializers.FloatField()
    type = serializers.CharField(max_length=10)
    placed_by = serializers.IntegerField()

    def create(self, validated_data):
        #Create and return a new 'Marker' instance, given the validated data.
        return Marker.objects.create(**validated_data)
#this might not be Marker, but actually snackvision
   
    def update(self, instance, validated_data):
        #Update and return an existing 'Marker' isntance, given the validated data.
        instance.lat = validated_data.get('lat', instance.lat)
        instance.lon = validated_data.get('lon', instance.lon)
        instance.type = validated_data.get('type', instance.type)
        instance.placed_by = validated_data.get('placed_by', instance.placed_by)
        instance.save()
        return instance


