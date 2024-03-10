# Instruction
1. Build docker image
    ```shell
    docker buildx build -t delivery-service:0.0.1 .
    ```
2. Add tag to image
    ```shell
    docker tag delivery-service:0.0.1 otuslearning/delivery-service:0.0.1
    ```
3. Push image
    ```shell
    docker push otuslearning/delivery-service:0.0.1
    ```