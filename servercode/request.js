const request=require('request')
request.get('http://localhost:3000/post',
    {'auth':
{
    'user':'usernames',
    'pass':'password',
    'sendImmediately':true
}}
)