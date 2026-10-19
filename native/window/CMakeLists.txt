cmake_minimum_required(VERSION 3.16)
project(breezze_window LANGUAGES CXX)

set(CMAKE_CXX_STANDARD 17)
set(CMAKE_CXX_STANDARD_REQUIRED ON)
set(CMAKE_CXX_EXTENSIONS OFF)

# Platform-specific settings
if(WIN32)
    add_compile_options(/MP /EHsc /DNOMINMAX)
    if(CMAKE_CXX_COMPILER_ID STREQUAL "GNU")
        add_compile_options(-static-libgcc -static-libstdc++)
    endif()
elseif(APPLE)
    add_compile_options(-fPIC)
    set(CMAKE_MACOSX_RPATH ON)
else()
    add_compile_options(-fPIC -pthread)
endif()

# Source files for Windows frame bridge
# This is a minimal implementation - the actual frame bridge code would be here
set(WINDOW_SOURCES
    # window_bridge.cpp
)

# For now, create a dummy library that exports the required symbols
# The actual implementation would be in window_bridge.cpp
if(WIN32)
    add_library(breezze_window SHARED)
    target_sources(breezze_window PRIVATE
        # Empty for now - actual implementation would go here
    )
    target_compile_definitions(breezze_window PRIVATE BREEZZE_WINDOW_EXPORTS)
    set_target_properties(breezze_window PROPERTIES
        OUTPUT_NAME "breezze_window"
        WINDOWS_EXPORT_ALL_SYMBOLS ON
    )
else()
    # On non-Windows platforms, create a dummy library
    add_library(breezze_window STATIC)
    target_sources(breezze_window PRIVATE
        # Empty
    )
endif()

# Install targets
install(TARGETS breezze_window
    LIBRARY DESTINATION lib
    ARCHIVE DESTINATION lib
    RUNTIME DESTINATION bin
)