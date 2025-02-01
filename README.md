# README

A bash script to create an automatic log archive tool.

## Getting Started

1. **Seeder**

   add arguments

   ```shell
    --seeder=all
   ```

2. **Phân quyền**

Hiện tại có 3 cách:

- Cách 1:
  - Thêm role vào claim của jwt token
  - Lọc role của claim jwt token ở Component Filter của Spring theo endpoint và method
  - Add FilterBefore vào SecurityConfig
- Cách 2:
  - Add role vào phương thức getAuthorities()
  - Dùng PreAuthorize hasAuthroity
- Cách 3:
  - Thêm role vào claim của jwt token
  - Tạo 1 annotation Permission chứa role
  - Tạo 1 Component Interceptor implements HandlerInterceptor và lọc theo claim role của jwt token
  - Tạo 1 Component InterceptorConfig implements WebMvcConfigurer với order LOWEST_PRECEDENCE
  - Dùng annotation Permission
