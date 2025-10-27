terraform {
  required_version = ">= 1.0"

  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "~> 5.0"
    }
  }

  backend "s3" {
    bucket         = "prod-state-bucketpsd"      # Nombre del bucket S3 donde guardarás el estado
    key            = "terraform.tfstate" # Ruta dentro del bucket
    region         = "us-east-1"                      # Región del bucket
    dynamodb_table = "terraform-locks"                # (Opcional, para bloqueo del estado)
    encrypt        = true                             # Encripta el archivo de estado en S3
  }
}

provider "aws" {
  region = var.aws_region
}
# ECR Repository
resource "aws_ecr_repository" "devops_microservice" {
  name = "devops-microservice"

  image_scanning_configuration {
    scan_on_push = true
  }
}

# EKS Cluster
resource "aws_eks_cluster" "devops_cluster" {
  name     = var.cluster_name
  role_arn = aws_iam_role.eks_cluster.arn
  version  = "1.29"  #

  vpc_config {
    subnet_ids = [aws_subnet.public_1.id, aws_subnet.public_2.id]
  }

  depends_on = [
    aws_iam_role_policy_attachment.eks_cluster_policy,
  ]
}

# EKS Node Group
resource "aws_eks_node_group" "devops_nodes" {
  cluster_name    = aws_eks_cluster.devops_cluster.name
  node_group_name = var.node_group_name
  node_role_arn   = aws_iam_role.eks_node.arn
  subnet_ids      = [aws_subnet.public_1.id, aws_subnet.public_2.id]

  scaling_config {
    desired_size = 2
    max_size     = 3
    min_size     = 1
  }

  instance_types = ["t3.small"]

  depends_on = [
    aws_iam_role_policy_attachment.eks_worker_node_policy,
    aws_iam_role_policy_attachment.eks_cni_policy,
  ]
}