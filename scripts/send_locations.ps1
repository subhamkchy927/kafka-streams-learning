param(
    [string]$DriverId = "driver1",
    [int]$Count = 1000,
    [int]$IntervalMs = 1000,
    [double]$StartLat = 12.9716,
    [double]$StartLng = 77.5946,
    [double]$Delta = 0.0005,
    [string]$Url = "http://localhost:8081/location/publish"
)

Write-Host "Starting stream: DriverId=$DriverId Count=$Count IntervalMs=$IntervalMs Url=$Url"

for ($i=0; $i -lt $Count; $i++) {
    $lat = $StartLat + ($i * $Delta)
    $lng = $StartLng + ($i * $Delta)
    $body = @{
        driverId  = $DriverId
        latitude  = [math]::Round($lat, 6)
        longitude = [math]::Round($lng, 6)
        timestamp = [int64]([DateTimeOffset]::UtcNow.ToUnixTimeMilliseconds())
    }

    try {
        Invoke-RestMethod -Uri $Url -Method Post -Body ($body | ConvertTo-Json -Depth 5) -ContentType "application/json"
        Write-Host ("Sent #{0}: {1}" -f ($i+1), ($body | ConvertTo-Json -Compress))
    } catch {
        Write-Host ("Error sending #{0}: {1}" -f ($i+1, $_.Exception.Message)) -ForegroundColor Red
    }

    Start-Sleep -Milliseconds $IntervalMs
}

Write-Host "Done."