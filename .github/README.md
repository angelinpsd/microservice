Reto Técnico DevOps 

Este proyecto implementa un microservicio desplegado sobre AWS EKS mediante un pipeline CI/CD completamente automatizado.
Cumple con los requisitos de seguridad, contenedorización, balanceo de carga y despliegue continuo descritos en el ejercicio técnico.

1. Infraestructura

La infraestructura fue creada utilizando Terraform y desplegada en AWS.

Servicios AWS utilizados:

EKS (Elastic Kubernetes Service): cluster administrado de Kubernetes.

ECR (Elastic Container Registry): repositorio privado de imágenes Docker.

IAM: control de accesos y permisos.

VPC/Subnets/Security Groups: red y seguridad para el cluster.

S3: bucket prod-state-bucketpsd utilizado para almacenar el Terraform state remoto.

DynamoDB: tabla terraform-locks utilizada para gestionar el state locking y evitar ejecuciones simultáneas de Terraform.

2. Aplicación

El microservicio fue desarrollado en Java con Spring Boot, exponiendo un único endpoint:

POST /DevOps

Seguridad

Requiere una API Key:

X-Parse-REST-API-Key: 2f5ae96c-b558-4c7b-a590-a501ae1c3f6c


Requiere un JWT único por transacción (X-JWT-KWY).

Respuesta esperada
{
"message": "Hello Juan Perez your message will be sent"
}

Contenedor Docker

El microservicio se empaqueta en una imagen Docker.

Kubernetes

Manifiestos principales:

deployment.yml

    Despliega 2 réplicas del microservicio.
Define recursos y puertos.

    Imagen proveniente del ECR.

service.yml
Expone el servicio mediante un LoadBalancer AWS.
Mapea el puerto 80 → 8080 para acceso externo.

3. CI/CD

El pipeline CI/CD se implementó en GitHub Actions, con los siguientes stages:

Etapa	Descripción
1.  Build & Test	Compila el proyecto con Maven y ejecuta tests automatizados (mvn clean package -DskipTests).
2. Build Container	Construye la imagen Docker y la etiqueta para ECR.
3. Push to ECR	Autentica con AWS y publica la imagen en el repositorio ECR.
4. Deploy to EKS	Aplica los manifiestos Kubernetes (deployment.yml, service.yml) en el cluster EKS.
5. Verify Deployment	Espera el rollout y verifica la URL del LoadBalancer.

El pipeline también realiza:

4. Consumo de la Aplicación

Ejemplo de consumo vía curl:

curl -X POST \
-H "X-Parse-REST-API-Key: 2f5ae96c-b558-4c7b-a590-a501ae1c3f6c" \
-H "X-JWT-KWY: ${JWT}" \
-H "Content-Type: application/json" \
-d '{ "message": "This is a test", "to": "Juan Perez", "from": "Rita Asturia", "timeToLifeSec": 45 }' \
http://0B8404940A359A544C1A5C000FE6E6A9.gr7.us-east-1.eks.amazonaws.com/DevOps


Respuesta esperada:

{
"message": "Hello Juan Perez your message will be sent"
}

5. Validación del Reto

Microservicio REST funcional y seguro.

Contenedorización Docker.

Infraestructura como código con Terraform.

Despliegue automático en EKS con balanceo de carga.

Pipeline CI/CD en GitHub Actions.

Pruebas automáticas y análisis estático de código.

Repositorio:
https://github.com/angelinpsd/microservice