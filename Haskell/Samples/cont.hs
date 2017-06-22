module Main where
import Control.Parallel
import Control.Concurrent
import Control.Concurrent.MVar

oper :: (Int -> Int -> Int) -> MVar Int -> MVar Int -> Int -> IO()
oper operation contador fim 0 =
    do v <- takeMVar contador
        putStrLn (show v)
        a <- takeMVar fim
        putMVar fim (a - 1)
oper operation contador fim num =
    do v <- takeMVar contador
        putMVar contador (operation v 1)
        oper operation contador fim (num - 1)

waitThreads :: MVar Int -> IO()
waitThreads fim =
    do f <- takeMVar fim
        if (f > 0) then
            do putMVar fim f
                waitThreads fim
        else
            return ()

main ::  IO()
main = do
    contador <- newMVar 0
    fim <- newMVar 2
    forkIO (oper (+) contador fim 1000000)
    forkIO (oper (-) contador fim 1000000)
    waitThreads fim
    return ()