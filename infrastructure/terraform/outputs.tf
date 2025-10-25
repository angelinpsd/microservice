output "cluster_endpoint" {
  value = aws_eks_cluster.devops_cluster.endpoint
}

output "cluster_name" {
  value = aws_eks_cluster.devops_cluster.name
}

output "ecr_repository_url" {
  value = aws_ecr_repository.devops_microservice.repository_url
}