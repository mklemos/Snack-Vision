from django.shortcuts import render
from django.contrib.auth.models import User, Group
from backend.snack_vision.models import new_Node
from rest_framework import viewsets
from backend.snack_vision.serializers import UserSerializer, GroupSerializer, new_NodeSerializer


class UserViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = User.objects.all().order_by('-date_joined')
    serializer_class = UserSerializer


class GroupViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows groups to be viewed or edited.
    """
    queryset = Group.objects.all()
    serializer_class = GroupSerializer

class new_NodeViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows nodes to be viewed or edited.
    """
    queryset = new_Node.objects.all()
    serializer_class = new_NodeSerializer
