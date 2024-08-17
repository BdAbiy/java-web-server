@echo off 
cls
echo updating classes ...
echo main.class
javac main.java
echo Webserver.class
javac Webserver.java
echo RequestReader.class
javac RequestReader.java
echo done
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
