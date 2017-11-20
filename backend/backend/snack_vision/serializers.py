from django.contrib.auth.models import User, Group
from backend.snack_vision.models import new_Node
from rest_framework import serializers


class UserSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = User
        fields = ('url', 'username', 'email', 'groups', 'is_student', 'token_push', 'token_id', 'push_yes')


class GroupSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Group
        fields = ('url', 'name')

class new_NodeSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = new_Node
        fields = ('name','loc_long','loc_lat', 'node_type', 'create_time', 'placed_by')

# class EventSerializer(serializers.HyperlinkedModelSerializer):
#    class Meta:
#        model = Event
#        fields = ('description', 'end_time', 'start_time')
