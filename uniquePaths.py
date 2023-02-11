# https://leetcode.com/problems/unique-paths-ii/

class Solution:
    # bottom up dp    
    def uniquePathsWithObstacles(self, obstacleGrid: List[List[int]]) -> int:
        if obstacleGrid[0][0] == 1:
            return 0 
        dp = [[0 for x in obstacleGrid[0]] for y in obstacleGrid]
        dp[0][0] = 1
        # define the border of the dp table
        for x in range(1, len(obstacleGrid[0])):
            if obstacleGrid[0][x] != 1:
                dp[0][x] = dp[0][x - 1] 
        for x in range(1, len(obstacleGrid)):
            if obstacleGrid[x][0] != 1:
                dp[x][0] = dp[x - 1][0] 
                
        for x in range(1, len(obstacleGrid[0])):
            for y in range(1, len(obstacleGrid)):
                if obstacleGrid[y][x] != 1:
                    dp[y][x] = dp[y - 1][x] + dp[y][x - 1]
                    
        return dp[len(obstacleGrid) - 1][len(obstacleGrid[0]) - 1]
