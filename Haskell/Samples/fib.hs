module Main where
import Control.Parallel
import Control.Concurrent
import Control.Concurrent.MVar

fib :: Int -> Int
fib 0 = 1
fib 1 = 1
fib n = fib (n - 1) + fib (n - 2)

fibThread :: Int -> MVar Int -> IO()
fibThread n ansMVar = pseq f (putMVar ansMVar f)
    where
        f = fib n

main ::  IO()
main = do
    putStrLn "Digite um valor pra saber seu fibonacci:"
    val <- getLine
    fibResult <- newEmptyMVar
    forkIO (fibThread (read val) fibResult)
    ans <- takeMVar fibResult
    putStrLn (show ans)