#!/bin/bash

echo "=== VERIFICACIÓN FINAL PERFECTA ==="
echo ""

# 1. Verificar POST exitoso con JWT
echo "1. ✅ POST exitoso con API Key correcta:"
response1=$(curl -i -s -X POST \
  -H "X-Parse-REST-API-Key: 2f5ae96c-b558-4c7b-a590-a501ae1c3f6c" \
  -H "Content-Type: application/json" \
  -d '{ "message": "This is a test", "to": "Juan Perez", "from": "Rita Asturia", "timeToLifeSec": 45 }' \
  http://localhost:8080/DevOps)

jwt1=$(echo "$response1" | grep "X-JWT-KWY" | cut -d' ' -f2 | tr -d '\r')
body1=$(echo "$response1" | tail -1)

echo "   JWT: $jwt1"
echo "   Body: $body1"
echo ""

# 2. Verificar JWT único
echo "2. ✅ Verificando JWT único por transacción:"
response2=$(curl -i -s -X POST \
  -H "X-Parse-REST-API-Key: 2f5ae96c-b558-4c7b-a590-a501ae1c3f6c" \
  -H "Content-Type: application/json" \
  -d '{ "message": "This is a test", "to": "Juan Perez", "from": "Rita Asturia", "timeToLifeSec": 45 }' \
  http://localhost:8080/DevOps)

jwt2=$(echo "$response2" | grep "X-JWT-KWY" | cut -d' ' -f2 | tr -d '\r')

echo "   JWT 1: $jwt1"
echo "   JWT 2: $jwt2"

if [ -n "$jwt1" ] && [ -n "$jwt2" ] && [ "$jwt1" != "$jwt2" ]; then
    echo "   ✅ JWT único: CORRECTO - Son diferentes"
else
    echo "   ❌ JWT único: FALLÓ"
fi
echo ""

# 3. Verificar métodos no permitidos
echo "3. ✅ Métodos no permitidos (deben devolver solo 'ERROR'):"
echo "   GET: $(curl -s -X GET http://localhost:8080/DevOps)"
echo "   PUT: $(curl -s -X PUT http://localhost:8080/DevOps)"
echo "   DELETE: $(curl -s -X DELETE http://localhost:8080/DevOps)"
echo ""

# 4. Verificar sin API Key
echo "4. ✅ Sin API Key (debe devolver 'ERROR'):"
response_no_key=$(curl -s -X POST \
  -H "Content-Type: application/json" \
  -d '{ "message": "This is a test", "to": "Juan Perez", "from": "Rita Asturia", "timeToLifeSec": 45 }' \
  http://localhost:8080/DevOps)
echo "   Respuesta: $response_no_key"
echo ""

# 5. Verificar API Key inválida
echo "5. ✅ API Key inválida (debe devolver 'ERROR'):"
response_invalid_key=$(curl -s -X POST \
  -H "X-Parse-REST-API-Key: key-invalida" \
  -H "Content-Type: application/json" \
  -d '{ "message": "This is a test", "to": "Juan Perez", "from": "Rita Asturia", "timeToLifeSec": 45 }' \
  http://localhost:8080/DevOps)
echo "   Respuesta: $response_invalid_key"
echo ""

# 6. Health check
echo "6. ✅ Health check:"
echo "   $(curl -s http://localhost:8080/health)"
echo ""

echo "=== VERIFICACIÓN COMPLETADA ==="