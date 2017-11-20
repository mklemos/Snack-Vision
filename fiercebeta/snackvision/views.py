from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.views.decorators.csrf import csrf_exempt
from rest_framework.renderers import JSONRenderer
from rest_framework.parsers import JSONParser
from snackvision.models import Marker
from snackvision.serializers import SnackvisionSerializer

@csrf_exempt
def marker_list(request):
    #List all markers, or create a new marker.
    if requested.method == 'GET':
        marker = Marker.objects.all()
        serializer = SnackvisionSerializer(marker, many=True)
        return JsonResponse(serializer.data, safe=False)

    elif request.method == 'POST':
        data = JSONParser().parse(request)
        serializer = SnackvisionSerializer(data=data)
        if serializer.is_valid():
            serializer.save()
            return JsonResponse(serializer.data, status=201)
        return JsonResponse(serializer.errors, status=400)

@csrf_exempt
def marker_detail(request, pk):
    #Retrieve, update or delete a marker.
    try:
        marker = Marker.objects.get(pk=pk)
    except Marker.DoesNotExist:
        return HttpResonse(status=404)

    if request.method =='GET':
        serializer = SnackvisionSerializer(marker)
        return JsonResponse(serializer.data)

    elif request.method == 'PUT':
        data = JSONParser().parse(request)
        serializer = SnackvisionSerializer(marker, data=data)
        if serializer.is_valid():
            serializer.save()
            return JsonResponse(serializer.data)
        return JsonResponse(serializer.errors, status=400)

    elif request.method == 'DELETE':
        marker.delete()
        return HttpResonse(status=204)
