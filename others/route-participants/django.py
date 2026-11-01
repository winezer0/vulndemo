from django.urls import path
def serve(request):
    return 'ok'
urlpatterns = [path('django', serve)]
