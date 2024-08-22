@echo off
cls
echo Updating classes ...
echo Compiling main.java
javac source/main.java
echo Compiling Webserver.java
javac  source/Webserver.java
echo Compiling RequestReader.java
javac - source/RequestReader.java
echo moving
move source/*.class .
echo Done

echo [                    ]
for /l %%i in (1,1,10000) do (
    if %%i == 1000 (
        cls
        echo [##                  ]
    )
    if %%i == 2000 (
        cls
        echo [####                ]
    )
    if %%i == 3000 (
        cls
        echo [######              ]
    )
    if %%i == 4000 (
        cls
        echo [########            ]
    )
    if %%i == 5000 (
        cls
        echo [##########          ]
    )
    if %%i == 6000 (
        cls
        echo [############        ]
    )
    if %%i == 7000 (
        cls
        echo [##############      ]
    )
    if %%i == 8000 (
        cls
        echo [################    ]
    )
    if %%i == 9000 (
        cls
        echo [##################  ]
    )
    if %%i == 10000 (
        cls
        echo [####################]
    )
)
@echo off
echo running main class...
cls
java main
