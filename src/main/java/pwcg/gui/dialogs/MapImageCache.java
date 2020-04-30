package pwcg.gui.dialogs;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.lang.ref.SoftReference;
import java.util.HashMap;

import pwcg.core.exception.PWCGException;
import pwcg.core.utils.PWCGLogger;
import pwcg.core.utils.PWCGLogger.LogLevel;
import pwcg.gui.utils.ContextSpecificImages;

public class MapImageCache
{
    private static MapImageCache instance = null;
    private static HashMap<String, SoftReference<BufferedImage>> bufferedMapImageCache = new HashMap<>();

    private MapImageCache()
    {
    }

    public static MapImageCache getInstance()
    {
        if (instance == null)
        {
            instance = new MapImageCache();
        }

        return instance;
    }

    public BufferedImage getMapImage(String mapImageFileName) throws PWCGException
    {
        synchronized (this)
        {
            BufferedImage mapImageForDisplay = getImageFromCache(mapImageFileName);
            if (mapImageForDisplay != null)
            {
                return mapImageForDisplay;
            }

            mapImageForDisplay = buildMapWithOverlay(mapImageFileName);
            if (mapImageForDisplay != null)
            {
                bufferedMapImageCache.put(mapImageFileName, new SoftReference<>(mapImageForDisplay));
                return mapImageForDisplay;
            }

            PWCGLogger.log(LogLevel.ERROR, "Image not found: " + mapImageFileName);
        }

        return null;
    }

    private BufferedImage getImageFromCache(String mapImageFileName)
    {
        SoftReference<BufferedImage> ref;
        BufferedImage image;
        ref = bufferedMapImageCache.get(mapImageFileName);
        image = ref != null ? ref.get() : null;
        if (image != null)
        {
            return image;
        }
        return image;
    }

    private BufferedImage buildMapWithOverlay(String mapImageFileName) throws PWCGException
    {
        BufferedImage mapOverlay = MapImageOverlay.getMapImage(mapImageFileName);
        BufferedImage mapImage = getMapImageNoOverlay(mapImageFileName);

        BufferedImage mapImageForDisplay = null;
        if (mapOverlay != null && mapImage != null)
        {
            BufferedImage combinedMapImage = combineOverlayWithMap(mapImage, mapOverlay);
            if (combinedMapImage != null)
            {
                mapImageForDisplay = combinedMapImage;
            }
            else
            {
                mapImageForDisplay = mapImage;
            }
        }
        else if (mapImage != null)
        {
            mapImageForDisplay = mapImage;
        }

        return mapImageForDisplay;
    }

    private BufferedImage getMapImageNoOverlay(String mapImageFileName) throws PWCGException
    {
        String mapFullPath = ContextSpecificImages.imagesMaps() + mapImageFileName + ".jpg";
        BufferedImage mapImage = ImageRetriever.getImageFromFile(mapFullPath);
        return mapImage;
    }

    private BufferedImage combineOverlayWithMap(BufferedImage mapImage, BufferedImage mapOverlay)
    {
        BufferedImage result = new BufferedImage(mapImage.getWidth(), mapImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();
        g.drawImage(mapImage, 0, 0, null);
        g.drawImage(mapOverlay, 0, 0, null);
        return result;
    }
}
