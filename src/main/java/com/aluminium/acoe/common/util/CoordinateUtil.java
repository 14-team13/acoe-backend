package com.aluminium.acoe.common.util;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.osgeo.proj4j.*;


@Slf4j
@RequiredArgsConstructor
public class CoordinateUtil {
    public static ProjCoordinate transformWGS84(double sourceX, double sourceY, String source) {
        CoordinateReferenceSystem sourceCRS = new CRSFactory().createFromName(source);

        // WGS84 좌표계
        CoordinateReferenceSystem targetCRS = new CRSFactory().createFromName("EPSG:4326");

        // 좌표 변환 객체 생성
        CoordinateTransformFactory ctf = new CoordinateTransformFactory();
        CoordinateTransform transform = ctf.createTransform(sourceCRS, targetCRS);

        // 좌표 변환
        ProjCoordinate sourceCoord = new ProjCoordinate(sourceX, sourceY);
        ProjCoordinate targetCoord = new ProjCoordinate();
        return transform.transform(sourceCoord, targetCoord);
    }
}
