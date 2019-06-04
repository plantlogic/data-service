[![Build Status](https://travis-ci.org/plantlogic/data-service.svg?branch=master)](https://travis-ci.org/plantlogic/data-service) 
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/07ab3bedc4614786a230a665abaab8ba)](https://www.codacy.com/app/mattwebbio/data-service?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=plantlogic/data-service&amp;utm_campaign=Badge_Grade)
[![Issues](https://img.shields.io/github/issues/plantlogic/data-service.svg?style=flat)](https://github.com/plantlogic/data-service/issues) 
[![License](https://img.shields.io/github/license/plantlogic/data-service.svg?style=flat)](https://github.com/plantlogic/data-service/blob/master/LICENSE) 
[![Docker Pulls](https://img.shields.io/docker/pulls/plantlogic/data-service.svg?style=flat)](https://hub.docker.com/r/plantlogic/data-service) 
[![Demo](https://img.shields.io/badge/demo-live-success.svg)](https://demo.plantlogic.org)
# 🌱 PlantLogic | Data Service

## Docker Environment Variables
### App Configuration
* **ENABLE_SWAGGER:** Default is `false`. Allows all connections to Swagger and Swagger UI.
* **ALLOW_COMMON_RESET:** Default is `false`. Deletes all 'common' data, but not ranch data.
* **SERVER_SERVLET_CONTEXT_PATH**: The path that the service listens to. If served from `example.com/api/user/`, this should be 
`/api/user` (which is the default).