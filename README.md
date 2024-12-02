<!-- START doctoc generated TOC please keep comment here to allow auto update -->
<!-- DON'T EDIT THIS SECTION, INSTEAD RE-RUN doctoc TO UPDATE -->

<!-- END doctoc generated TOC please keep comment here to allow auto update -->

[![Airthings][logo]](https://www.airthings.com)

# lib-kotless

This repository is used to maintain the code base for the kotlin library lib-kotless.
This code base in this repo is maintained by the Airthings kotlin_core_maintainers team. So please reach out to them before making any breaking changes.

[logo]: https://upload.wikimedia.org/wikipedia/commons/d/d1/Airthings_logo.svg

# Contribute
When a PR is made to this repository, make sure to add a label corresponding to the type of change you are making. The labels are:
- `major` : For any breaking changes
- `minor` : For any new features
- `patch` : For any bug fixes

## Installation

Declare the dependency in your `build.gradle` file:

- Add this helper function
    ```kotlin
  fun RepositoryHandler.airthings(repository: String) = maven {
    name = "com.airthings.github.packages"
    url = uri("https://maven.pkg.github.com/Airthings/lib-kotless")
    credentials {
      username = env.fetchOrNull("GITHUB_USERNAME")
        ?: System.getenv("GITHUB_USERNAME")
        ?: System.getenv("GITHUB_ACTOR")
      password = env.fetchOrNull("GITHUB_ACCESS_TOKEN_READ_PACKAGES")
            ?: System.getenv("GITHUB_ACCESS_TOKEN_READ_PACKAGES")
        }
      }
   ```

- Add the repository
    ```kotlin
    repositories { airthings(repository = "lib-kotless") }
    ```

- Add the dependency using catalog
    ```kotlin
  dependencies { implementation(libs.airthings.replace.me) }
  ```

## Usage

Use Example.client() to create a client instance.
