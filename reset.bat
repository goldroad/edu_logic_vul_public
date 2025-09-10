@echo off
@chcp 65001 >nul

echo 正在将代码恢复到上一个提交的版本...

:: 切换到代码仓库的根目录
cd /d "C:\Users\Administrator\CodeBuddy\edu_logic_vul"

:: 检查是否成功切换到仓库目录
if %errorlevel% neq 0 (
    echo 无法切换到仓库目录，请检查路径是否正确。
    exit /b 1
)

:: 检查当前目录是否为 Git 仓库
git rev-parse --is-inside-work-tree >nul 2>&1
if %errorlevel% neq 0 (
    echo 当前目录不是 Git 仓库，请检查路径是否正确。
    exit /b 1
)

:: 获取当前分支
for /f "delims=" %%i in ('git rev-parse --abbrev-ref HEAD 2^>nul') do set current_branch=%%i

:: 检查是否成功获取当前分支
if not defined current_branch (
    echo 无法获取当前分支，请检查 Git 仓库状态。
    exit /b 1
)

echo 当前分支: %current_branch%

:: 检查提交数量
for /f %%i in ('git rev-list --count HEAD') do set commit_count=%%i

:: 检查提交数量
if %commit_count% lss 2 (
    echo 仓库中只有一个提交，